import React from 'react';
import styled from 'styled-components';
import { getServingsCount, getYieldCount } from '../data/models';
import type { Recipe } from '../data/models';

interface RecipeServingsProps {
  recipe: Recipe;
}

const ServingsContainer = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 8px;
  color: ${({ theme }) => theme.colors.foregroundSupport};
  font-size: 0.875rem;
`;

const ServingsText = styled.span`
  margin-right: 8px;
`;

const RecipeServings: React.FC<RecipeServingsProps> = ({ recipe }) => {
  const servingsCount = getServingsCount(recipe);
  const yieldCount = getYieldCount(recipe);

  if (servingsCount <= 0 && yieldCount <= 0) {
    return null;
  }

  return (
    <ServingsContainer>
      {servingsCount > 0 && (
        <ServingsText>
          {servingsCount} {servingsCount === 1 ? 'serving' : 'servings'}
        </ServingsText>
      )}

      {yieldCount > 0 && recipe.recipeYield && (
        <ServingsText>
          {yieldCount} {recipe.recipeYield}
        </ServingsText>
      )}
    </ServingsContainer>
  );
};

export default RecipeServings;
