import React, { useMemo } from 'react';
import styled from 'styled-components';
import type { Recipe } from '../data/models';
import HighlightedRecipeCard from './HighlightedRecipeCard';
import HorizontalPager from './HorizontalPager';

interface DessertRecipesProps {
  recipes: Recipe[];
  onRecipeClick: (recipe: Recipe) => void;
}

const Section = styled.section`
  margin-bottom: 60px;
`;

const SectionTitle = styled.h2`
  margin-bottom: 8px;
  color: ${({ theme }) => theme.colors.foregroundDefault};
  padding: 0 16px;
`;

const SectionSubtitle = styled.p`
  margin-bottom: 16px;
  color: ${({ theme }) => theme.colors.foregroundSupport};
  padding: 0 16px;
`;

const PageContainer = styled.div`
  height: 450px;

  @media (max-width: 767px) {
    height: 350px;
  }
`;

const CardRow = styled.div`
  display: flex;
  height: 100%;
  gap: 16px;
`;

const CardWrapper = styled.div`
  flex: 1;
  min-width: 0; // Prevents flex items from overflowing
`;

const DessertRecipes: React.FC<DessertRecipesProps> = ({ recipes, onRecipeClick }) => {
  // Group recipes into chunks of 2 for each page
  const chunkedRecipes = useMemo(() => {
    return recipes.reduce<Recipe[][]>((result, recipe, index) => {
      const chunkIndex = Math.floor(index / 2);

      if (!result[chunkIndex]) {
        result[chunkIndex] = [];
      }

      result[chunkIndex].push(recipe);
      return result;
    }, []);
  }, [recipes]);

  // Determine if we should show two pages side by side on larger screens
  const isLargeScreen = window.innerWidth >= 700;
  const pageSize = isLargeScreen ? (window.innerWidth - 48) / 2 : 'fill';

  if (recipes.length === 0) {
    return null;
  }

  return (
    <Section>
      <SectionTitle>Desserts and baked goods</SectionTitle>
      <SectionSubtitle>When the moment calls for a bake-off</SectionSubtitle>

      <HorizontalPager 
        pageSize={pageSize}
        pageSpacing={16}
        contentPadding={{ left: 16, right: 16 }}
        showPagination={isLargeScreen}
      >
        {chunkedRecipes.map((recipeGroup, pageIndex) => (
          <PageContainer key={pageIndex}>
            <CardRow>
              {recipeGroup.map((recipe) => (
                <CardWrapper key={recipe.id}>
                  <HighlightedRecipeCard 
                    recipe={recipe} 
                    onRecipeClick={onRecipeClick} 
                  />
                </CardWrapper>
              ))}
              {/* Add empty space if there's only one recipe in the group */}
              {recipeGroup.length === 1 && <CardWrapper />}
            </CardRow>
          </PageContainer>
        ))}
      </HorizontalPager>
    </Section>
  );
};

export default DessertRecipes;
