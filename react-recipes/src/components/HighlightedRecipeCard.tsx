import React from 'react';
import styled from 'styled-components';
import { getImageUrl, getServingsCount, getYieldCount } from '../data/models';
import type { Recipe } from '../data/models';

interface HighlightedRecipeCardProps {
  recipe: Recipe;
  onRecipeClick: (recipe: Recipe) => void;
}

const Card = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  overflow: hidden;
  cursor: pointer;
  background-color: ${({ theme }) => theme.colors.backgroundSurface1};
  border-radius: 16px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
  transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  }
`;

const ImageContainer = styled.div`
  position: relative;
  width: 100%;
  height: 200px;
  overflow: hidden;

  @media (min-width: 768px) {
    height: 250px;
  }
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
  margin-bottom: 12px;
  font-size: 1.5rem;
  color: ${({ theme }) => theme.colors.foregroundDefault};
`;

const Description = styled.p`
  margin-bottom: 16px;
  color: ${({ theme }) => theme.colors.foregroundSupport};
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
`;

const MetaContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: auto;
`;

const ServingsInfo = styled.div`
  color: ${({ theme }) => theme.colors.foregroundSupport};
  font-size: 0.875rem;
`;

const TimeTag = styled.div`
  display: inline-block;
  padding: 8px 12px;
  background-color: ${({ theme }) => theme.colors.backgroundBrand};
  color: ${({ theme }) => theme.colors.onBackgroundBrand};
  border-radius: 8px;
  font-size: 0.875rem;
  font-weight: 500;
`;

const HighlightedRecipeCard: React.FC<HighlightedRecipeCardProps> = ({ recipe, onRecipeClick }) => {
  const servingsCount = getServingsCount(recipe);
  const yieldCount = getYieldCount(recipe);
  
  return (
    <Card onClick={() => onRecipeClick(recipe)}>
      <ImageContainer>
        <Image src={getImageUrl(recipe)} alt={recipe.name || 'Recipe'} />
      </ImageContainer>
      <Content>
        <Title>{recipe.name}</Title>
        <Description>{recipe.description}</Description>
        <MetaContainer>
          <ServingsInfo>
            {servingsCount > 0 && (
              <span>{servingsCount} {servingsCount === 1 ? 'serving' : 'servings'}</span>
            )}
            {yieldCount > 0 && recipe.recipeYield && (
              <span>{yieldCount} {recipe.recipeYield}</span>
            )}
          </ServingsInfo>
          {recipe.totalTime && recipe.totalTime.length > 0 && (
            <TimeTag>{recipe.totalTime} min</TimeTag>
          )}
        </MetaContainer>
      </Content>
    </Card>
  );
};

export default HighlightedRecipeCard;