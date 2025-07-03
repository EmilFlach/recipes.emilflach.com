import React from 'react';
import styled from 'styled-components';
import type { Ingredient } from '../data/models';

interface RecipeIngredientItemProps {
  ingredient: Ingredient;
}

const IngredientContainer = styled.div`
  padding: 8px 24px;
  display: flex;
  flex-direction: column;
`;

const IngredientText = styled.p`
  color: ${({ theme }) => theme.colors.foregroundDefault};
  font-size: 1rem;
  line-height: 1.5;
`;

const SectionTitle = styled.h4`
  margin-top: 16px;
  margin-bottom: 8px;
  color: ${({ theme }) => theme.colors.foregroundSupport};
  font-size: 1.1rem;
`;

const Note = styled.p`
  color: ${({ theme }) => theme.colors.foregroundSupport};
  font-size: 0.875rem;
  font-style: italic;
  margin-top: 4px;
`;

const RecipeIngredientItem: React.FC<RecipeIngredientItemProps> = ({ ingredient }) => {
  return (
    <IngredientContainer>
      {ingredient.sectionTitle && <SectionTitle>{ingredient.sectionTitle}</SectionTitle>}
      <IngredientText>{ingredient.text}</IngredientText>
      {ingredient.note && <Note>{ingredient.note}</Note>}
    </IngredientContainer>
  );
};

export default RecipeIngredientItem;
