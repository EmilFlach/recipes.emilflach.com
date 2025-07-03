import React from 'react';
import styled from 'styled-components';
import { getImageUrl } from '../data/models';
import type { Recipe } from '../data/models';
import RecipeServings from './RecipeServings';

interface RecipeCardProps {
  index: number;
  recipe: Recipe;
  onRecipeClick: (recipe: Recipe) => void;
}

const Card = styled.div<{ index: number }>`
  display: flex;
  flex-direction: row;
  height: 160px;
  overflow: hidden;
  cursor: pointer;
  background-color: ${({ theme }) => theme.colors.backgroundSurface1};
  border-radius: ${({ index }) => getCardRounding(index)};
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
  }
`;

const ImageContainer = styled.div`
  flex: 0 0 50%;
  height: 100%;
  overflow: hidden;
`;

const Image = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

const Content = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 16px;
`;

const Title = styled.h3`
  margin-bottom: 8px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const TimeTag = styled.div`
  display: inline-block;
  margin-top: auto;
  padding: 8px 12px;
  background-color: ${({ theme }) => theme.colors.backgroundBrand};
  color: ${({ theme }) => theme.colors.onBackgroundBrand};
  border-radius: 8px;
  font-size: 0.875rem;
  font-weight: 500;
`;

// Helper function to get card rounding based on index
const getCardRounding = (index: number): string => {
  switch (index) {
    case 0:
      return '16px 16px 4px 4px';
    case 2:
      return '4px 4px 16px 16px';
    default:
      return '4px';
  }
};

const RecipeCard: React.FC<RecipeCardProps> = ({ index, recipe, onRecipeClick }) => {
  return (
    <Card index={index} onClick={() => onRecipeClick(recipe)}>
      <ImageContainer>
        <Image src={getImageUrl(recipe)} alt={recipe.name || 'Recipe'} />
      </ImageContainer>
      <Content>
        <Title>{recipe.name}</Title>
        <RecipeServings recipe={recipe} />
        {recipe.totalTime && recipe.totalTime.length > 0 && (
          <TimeTag>{recipe.totalTime} min</TimeTag>
        )}
      </Content>
    </Card>
  );
};

export default RecipeCard;
