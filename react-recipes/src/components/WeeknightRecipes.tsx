import React, { useMemo } from 'react';
import styled from 'styled-components';
import type { Recipe } from '../data/models';
import RecipeCard from './RecipeCard';
import HorizontalPager from './HorizontalPager';

interface WeeknightRecipesProps {
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
  display: flex;
  flex-direction: column;
  height: 488px;
`;

const CardContainer = styled.div`
  margin-bottom: 8px;
  &:last-child {
    margin-bottom: 0;
  }
`;

const WeeknightRecipes: React.FC<WeeknightRecipesProps> = ({ recipes, onRecipeClick }) => {
  // Group recipes into chunks of 3 for each page
  const chunkedRecipes = useMemo(() => {
    return recipes.reduce<Recipe[][]>((result, recipe, index) => {
      const chunkIndex = Math.floor(index / 3);

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
      <SectionTitle>Weeknights</SectionTitle>
      <SectionSubtitle>Low effort and vegetarian</SectionSubtitle>

      <HorizontalPager 
        pageSize={pageSize}
        pageSpacing={16}
        contentPadding={{ left: 16, right: 16 }}
        showPagination={isLargeScreen}
      >
        {chunkedRecipes.map((recipeGroup, pageIndex) => (
          <PageContainer key={pageIndex}>
            {recipeGroup.map((recipe, index) => (
              <CardContainer key={recipe.id}>
                <RecipeCard 
                  index={index} 
                  recipe={recipe} 
                  onRecipeClick={onRecipeClick} 
                />
              </CardContainer>
            ))}
          </PageContainer>
        ))}
      </HorizontalPager>
    </Section>
  );
};

export default WeeknightRecipes;
