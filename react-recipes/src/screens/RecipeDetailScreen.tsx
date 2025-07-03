import React, { useState, useMemo, useCallback, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import styled from 'styled-components';
import { RecipeService, queryKeys } from '../data/api';
import { hasInstructionSections, getServingsCount } from '../data/models';
import type { Recipe, Instruction, Ingredient, InstructionSection } from '../data/models';
import {
  RecipeHeader,
  RecipeServingsScaler,
  RecipeIngredientItem,
  RecipeInstructionItem,
  RecipeInstructionSection,
} from '../components';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  background-color: ${({ theme }) => theme.colors.backgroundPage};
  min-height: 100vh;
`;

const Content = styled.div`
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 16px;

  @media (min-width: 768px) {
    padding: 0 32px;
  }
`;

const TitleContainer = styled.div`
  margin-top: -16px;
  padding: 24px;
  background-color: ${({ theme }) => theme.colors.backgroundPage};
  border-top-left-radius: 16px;
  border-top-right-radius: 16px;
  z-index: 1;
  position: relative;
`;

const Title = styled.h1`
  margin-bottom: 8px;
  color: ${({ theme }) => theme.colors.foregroundDefault};
`;

const SectionTitle = styled.h2`
  margin: 32px 0 16px;
  padding: 0 24px;
  color: ${({ theme }) => theme.colors.foregroundDefault};
`;

const IngredientsContainer = styled.div`
  margin-bottom: 32px;
`;

const InstructionsContainer = styled.div`
  margin-bottom: 32px;
`;

const BackButton = styled.button`
  position: fixed;
  top: 16px;
  left: 16px;
  z-index: 10;
  background-color: ${({ theme }) => theme.colors.backgroundSurface1};
  color: ${({ theme }) => theme.colors.foregroundDefault};
  border-radius: 50%;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);

  &:hover {
    background-color: ${({ theme }) => theme.colors.backgroundSurface1Hover};
  }
`;

const LoadingContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
`;

const ErrorContainer = styled.div`
  color: ${({ theme }) => theme.colors.foregroundDanger};
  text-align: center;
  padding: 32px;
`;

const CookingModeToggle = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 8px 16px;
  margin: 0 24px 16px;
  background-color: ${({ theme, active }: { theme: any; active?: boolean }) => 
    active ? theme.colors.backgroundBrand : theme.colors.backgroundSurface1};
  color: ${({ theme, active }: { theme: any; active?: boolean }) => 
    active ? theme.colors.onBackgroundBrand : theme.colors.foregroundDefault};
  border-radius: 8px;
  font-weight: 500;
  cursor: pointer;

  &:hover {
    background-color: ${({ theme, active }: { theme: any; active?: boolean }) => 
      active ? theme.colors.backgroundBrandHover : theme.colors.backgroundSurface1Hover};
  }
`;

const RecipeDetailScreen: React.FC = () => {
  const { slug } = useParams<{ slug: string }>();
  const navigate = useNavigate();

  // State for servings and cooking mode
  const [currentServings, setCurrentServings] = useState(0);
  const [isCookingMode, setIsCookingMode] = useState(false);
  const [currentInstructionIndex, setCurrentInstructionIndex] = useState(-1);
  const [expandedSections, setExpandedSections] = useState<Record<string, boolean>>({});

  // Refs for auto-scrolling
  const instructionRefs = useRef<Record<number, HTMLDivElement | null>>({});
  const instructionsContainerRef = useRef<HTMLDivElement | null>(null);

  // Fetch recipe data
  const { data: recipe, isLoading, isError, error } = useQuery({
    queryKey: queryKeys.recipe(slug || ''),
    queryFn: () => RecipeService.getRecipeBySlug(slug || ''),
    onSuccess: (data) => {
      // Initialize servings from recipe data
      if (currentServings === 0) {
        setCurrentServings(data.recipeServings ? Math.floor(data.recipeServings) : 1);
      }
    },
  });

  // Handle servings change
  const handleServingsChange = useCallback((servings: number) => {
    setCurrentServings(servings);
  }, []);

  // Handle cooking mode toggle
  const toggleCookingMode = useCallback(() => {
    setIsCookingMode(prev => !prev);
    if (!isCookingMode) {
      setCurrentInstructionIndex(0);
    } else {
      setCurrentInstructionIndex(-1);
    }
  }, [isCookingMode]);

  // Handle instruction click
  const handleInstructionClick = useCallback((index: number) => {
    if (isCookingMode) {
      setCurrentInstructionIndex(index);
    }
  }, [isCookingMode]);

  // Toggle section expansion
  const toggleSectionExpanded = useCallback((sectionTitle: string) => {
    setExpandedSections(prev => ({
      ...prev,
      [sectionTitle]: !prev[sectionTitle]
    }));
  }, []);

  // Auto-scroll to current instruction in cooking mode
  useEffect(() => {
    if (isCookingMode && currentInstructionIndex >= 0 && instructionRefs.current[currentInstructionIndex]) {
      instructionRefs.current[currentInstructionIndex]?.scrollIntoView({
        behavior: 'smooth',
        block: 'center'
      });
    }
  }, [currentInstructionIndex, isCookingMode]);

  // Initialize expanded sections when recipe loads
  useEffect(() => {
    if (recipe && hasInstructionSections(recipe)) {
      const initialExpandedState: Record<string, boolean> = {};
      sectionedInstructions.forEach(section => {
        initialExpandedState[section.title] = true; // Expand all sections by default
      });
      setExpandedSections(initialExpandedState);
    }
  }, [recipe, sectionedInstructions]);

  // Format ingredients with scaling
  const formattedIngredients = useMemo(() => {
    if (!recipe) return [];

    const originalServings = getServingsCount(recipe);
    const scaleFactor = originalServings > 0 ? currentServings / originalServings : 1;

    return recipe.recipeIngredient.map(ingredient => {
      let displayText = ingredient.display;

      // Scale the quantity if it's a food ingredient with a quantity
      if (ingredient.isFood && ingredient.quantity !== null && ingredient.quantity !== undefined) {
        const scaledQuantity = ingredient.quantity * scaleFactor;

        // Replace the original quantity with the scaled quantity in the display text
        // This is a simple approach - a more robust solution would parse and format properly
        if (ingredient.quantity.toString() && displayText.includes(ingredient.quantity.toString())) {
          displayText = displayText.replace(
            ingredient.quantity.toString(), 
            scaledQuantity.toFixed(scaledQuantity % 1 === 0 ? 0 : 1)
          );
        }
      }

      return {
        id: ingredient.referenceId,
        text: displayText,
        sectionTitle: ingredient.title || undefined,
        note: ingredient.note || undefined,
        url: undefined
      } as Ingredient;
    });
  }, [recipe, currentServings]);

  // Format instructions
  const instructions = useMemo(() => {
    if (!recipe) return [];

    return recipe.recipeInstructions.map((instruction, index) => {
      return {
        id: instruction.id,
        text: instruction.text,
        section: instruction.title || undefined,
        ingredients: [],
        globalIndex: index
      } as Instruction;
    });
  }, [recipe]);

  // Generate sectioned instructions
  const sectionedInstructions = useMemo(() => {
    if (!recipe || !hasInstructionSections(recipe)) return [];

    const sections: InstructionSection[] = [];
    let currentSection: InstructionSection | null = null;

    instructions.forEach(instruction => {
      if (instruction.section) {
        // Start a new section
        currentSection = {
          title: instruction.section,
          subtitle: instruction.text,
          instructions: []
        };
        sections.push(currentSection);
      } else if (currentSection) {
        // Add to current section
        currentSection.instructions.push(instruction);
      }
    });

    return sections;
  }, [recipe, instructions]);

  // Handle back button click
  const handleBackClick = useCallback(() => {
    navigate('/');
  }, [navigate]);

  if (isLoading) {
    return (
      <Container>
        <LoadingContainer>
          <p>Loading recipe...</p>
        </LoadingContainer>
      </Container>
    );
  }

  if (isError || !recipe) {
    return (
      <Container>
        <ErrorContainer>
          <h2>Error loading recipe</h2>
          <p>{(error as Error)?.message || 'An unknown error occurred'}</p>
        </ErrorContainer>
      </Container>
    );
  }

  return (
    <Container>
      <BackButton onClick={handleBackClick}>←</BackButton>
      <RecipeHeader recipe={recipe} />

      <Content>
        <TitleContainer>
          <Title>{recipe.name}</Title>
        </TitleContainer>

        <SectionTitle>Ingredients</SectionTitle>
        <RecipeServingsScaler
          recipe={recipe}
          currentServings={currentServings}
          onServingsChange={handleServingsChange}
        />

        <IngredientsContainer>
          {formattedIngredients.map((ingredient) => (
            <RecipeIngredientItem key={ingredient.id} ingredient={ingredient} />
          ))}
        </IngredientsContainer>

        <SectionTitle>Instructions</SectionTitle>
        <CookingModeToggle 
          active={isCookingMode} 
          onClick={toggleCookingMode}
        >
          {isCookingMode ? 'Exit Cooking Mode' : 'Enter Cooking Mode'}
        </CookingModeToggle>

        <InstructionsContainer ref={instructionsContainerRef}>
          {recipe && hasInstructionSections(recipe) ? (
            // Render sectioned instructions
            sectionedInstructions.map((section) => (
              <RecipeInstructionSection
                key={section.title}
                section={section}
                isExpanded={!!expandedSections[section.title]}
                onToggleExpanded={() => toggleSectionExpanded(section.title)}
                currentInstructionIndex={currentInstructionIndex}
                onInstructionClick={handleInstructionClick}
                instructionRefs={instructionRefs}
              />
            ))
          ) : (
            // Render flat list of instructions
            instructions.map((instruction, index) => (
              <RecipeInstructionItem
                key={instruction.id}
                instruction={instruction}
                index={index}
                isCurrent={index === currentInstructionIndex}
                onClick={() => handleInstructionClick(index)}
                ref={el => instructionRefs.current[index] = el}
              />
            ))
          )}
        </InstructionsContainer>
      </Content>
    </Container>
  );
};

export default RecipeDetailScreen;
