import React from 'react';
import styled from 'styled-components';
import type { Recipe } from '../data/models';
import HighlightedRecipeCard from './HighlightedRecipeCard';
import HorizontalPager from './HorizontalPager';

interface SpecialOccasionRecipesProps {
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
  height: 400px;

  @media (max-width: 767px) {
    height: 350px;
  }
`;

const SpecialOccasionRecipes: React.FC<SpecialOccasionRecipesProps> = ({ recipes, onRecipeClick }) => {
  // Determine if we should show two pages side by side on larger screens
  const isLargeScreen = window.innerWidth >= 700;
  const pageSize = isLargeScreen ? (window.innerWidth - 48) / 2 : 'fill';

  if (recipes.length === 0) {
    return null;
  }

  return (
    <Section>
      <SectionTitle>Special occasions</SectionTitle>
      <SectionSubtitle>Complex ingredients and prep, but worth the effort</SectionSubtitle>

      <HorizontalPager 
        pageSize={pageSize}
        pageSpacing={16}
        contentPadding={{ left: 16, right: 16 }}
        showPagination={isLargeScreen}
      >
        {recipes.map((recipe) => (
          <PageContainer key={recipe.id}>
            <HighlightedRecipeCard 
              recipe={recipe} 
              onRecipeClick={onRecipeClick} 
            />
          </PageContainer>
        ))}
      </HorizontalPager>
    </Section>
  );
};

export default SpecialOccasionRecipes;
