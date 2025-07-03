import React from 'react';
import styled from 'styled-components';
import { getServingsCount, getCalories } from '../data/models';
import type { Recipe } from '../data/models';

interface RecipeServingsScalerProps {
  recipe: Recipe;
  currentServings: number;
  onServingsChange: (servings: number) => void;
}

const ScalerContainer = styled.div`
  display: flex;
  align-items: center;
  padding: 0 24px 16px;
  margin-bottom: 16px;
  border-bottom: 1px solid ${({ theme }) => theme.colors.borderSeparator};
`;

const LabelContainer = styled.div`
  display: flex;
  align-items: baseline;
  margin-right: 16px;
`;

const Label = styled.span`
  color: ${({ theme }) => theme.colors.foregroundDefault};
  font-weight: 500;
`;

const CaloriesLabel = styled.span`
  margin-left: 8px;
  color: ${({ theme }) => theme.colors.foregroundSupport};
  font-size: 0.875rem;
`;

const ButtonGroup = styled.div`
  display: flex;
  align-items: center;
  background-color: ${({ theme }) => theme.colors.backgroundSurface1};
  border-radius: 8px;
  overflow: hidden;
`;

const Button = styled.button`
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: ${({ theme }) => theme.colors.backgroundBrand};
  color: ${({ theme }) => theme.colors.onBackgroundBrand};
  font-size: 1.25rem;
  font-weight: bold;
  cursor: pointer;
  border: none;

  &:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }

  &:hover:not(:disabled) {
    background-color: ${({ theme }) => theme.colors.backgroundBrandHover};
  }
`;

const ServingsDisplay = styled.div`
  padding: 0 16px;
  min-width: 40px;
  text-align: center;
  font-weight: 500;
`;

const RecipeServingsScaler: React.FC<RecipeServingsScalerProps> = ({
  recipe,
  currentServings,
  onServingsChange,
}) => {
  const originalServings = getServingsCount(recipe);

  const handleDecrease = () => {
    if (currentServings > 1) {
      onServingsChange(currentServings - 1);
    }
  };

  const handleIncrease = () => {
    onServingsChange(currentServings + 1);
  };

  if (originalServings <= 0) {
    return null;
  }

  const calories = getCalories(recipe);

  return (
    <ScalerContainer>
      <LabelContainer>
        <Label>Servings</Label>
        {calories && <CaloriesLabel>({calories} each)</CaloriesLabel>}
      </LabelContainer>
      <ButtonGroup>
        <Button onClick={handleDecrease} disabled={currentServings <= 1}>-</Button>
        <ServingsDisplay>{currentServings}</ServingsDisplay>
        <Button onClick={handleIncrease}>+</Button>
      </ButtonGroup>
    </ScalerContainer>
  );
};

export default RecipeServingsScaler;
