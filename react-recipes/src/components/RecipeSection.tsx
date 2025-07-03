import React from 'react';
import styled from 'styled-components';
import type { Recipe } from '../data/models';
import RecipeCard from './RecipeCard';

interface RecipeSectionProps {
  title: string;
  recipes: Recipe[];
  onRecipeClick: (recipe: Recipe) => void;
}

const Section = styled.section`
  margin-bottom: 32px;
`;

const SectionTitle = styled.h2`
  margin-bottom: 16px;
  color: ${({ theme }) => theme.colors.foregroundDefault};
`;

const RecipeGrid = styled.div`
  display: grid;
  grid-template-columns: 1fr;
  gap: 16px;

  @media (min-width: 768px) {
    grid-template-columns: repeat(2, 1fr);
  }

  @media (min-width: 1200px) {
    grid-template-columns: repeat(3, 1fr);
  }
`;

const RecipeSection: React.FC<RecipeSectionProps> = ({ title, recipes, onRecipeClick }) => {
  if (!recipes || recipes.length === 0) {
    return null;
  }

  return (
    <Section>
      <SectionTitle>{title}</SectionTitle>
      <RecipeGrid>
        {recipes.map((recipe, index) => (
          <RecipeCard 
            key={recipe.id} 
            index={index} 
            recipe={recipe} 
            onRecipeClick={onRecipeClick} 
          />
        ))}
      </RecipeGrid>
    </Section>
  );
};

export default RecipeSection;
