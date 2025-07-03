import React from 'react';
import styled from 'styled-components';
import { getImageUrl } from '../data/models';
import type { Recipe } from '../data/models';

interface RecipeHeaderProps {
  recipe: Recipe;
}

const HeaderContainer = styled.div`
  position: relative;
  width: 100%;
  height: 300px;

  @media (min-width: 768px) {
    height: 400px;
  }
`;

const HeaderImage = styled.div<{ imageUrl: string }>`
  width: 100%;
  height: 100%;
  background-image: url(${props => props.imageUrl});
  background-size: cover;
  background-position: center;
  position: relative;

  &::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    height: 100px;
    background: linear-gradient(to top, rgba(0, 0, 0, 0.5), transparent);
  }
`;

const BackButton = styled.button`
  position: absolute;
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

const RecipeHeader: React.FC<RecipeHeaderProps> = ({ recipe }) => {
  return (
    <HeaderContainer>
      <HeaderImage imageUrl={getImageUrl(recipe)} />
    </HeaderContainer>
  );
};

export default RecipeHeader;
