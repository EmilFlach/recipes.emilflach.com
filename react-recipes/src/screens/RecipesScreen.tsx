import React, { useMemo } from 'react';
import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { RecipeService, queryKeys } from '../data/api';
import type { Recipe } from '../data/models';
import { WeeknightRecipes, SpecialOccasionRecipes, DessertRecipes } from '../components';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0 16px;
  background-color: ${({ theme }) => theme.colors.backgroundPage};
  min-height: 100vh;
`;

const Content = styled.div`
  width: 100%;
  max-width: 1200px;
  padding: 32px 0;
`;

const Header = styled.header`
  text-align: center;
  margin-bottom: 48px;
  padding-top: 60px;
`;

const Title = styled.h1`
  margin-bottom: 16px;
  font-size: 3rem;

  @media (max-width: 768px) {
    font-size: 2rem;
  }
`;

const LoadingContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
`;

const ErrorContainer = styled.div`
  color: ${({ theme }) => theme.colors.foregroundDanger};
  text-align: center;
  padding: 32px;
`;

const RecipesScreen: React.FC = () => {
  const navigate = useNavigate();

  // Fetch recipes data
  const { data, isLoading, isError, error } = useQuery({
    queryKey: [queryKeys.recipes],
    queryFn: () => RecipeService.getRecipes(),
  });

  // Handle recipe click
  const handleRecipeClick = (recipe: Recipe) => {
    navigate(`/recipe/${recipe.slug}`);
  };

  // Filter recipes by category
  const weeknightRecipes = useMemo(() => {
    if (!data?.items) return [];
    return data.items.filter(recipe => 
      recipe.recipeCategory.some(category => category.name === 'Weeknight')
    );
  }, [data?.items]);

  const specialOccasionRecipes = useMemo(() => {
    if (!data?.items) return [];
    return data.items.filter(recipe => 
      recipe.recipeCategory.some(category => category.name === 'Special Occasion')
    );
  }, [data?.items]);

  const dessertRecipes = useMemo(() => {
    if (!data?.items) return [];
    return data.items.filter(recipe => 
      recipe.recipeCategory.some(category => category.name === 'Baking')
    );
  }, [data?.items]);

  if (isLoading) {
    return (
      <Container>
        <LoadingContainer>
          <p>Loading recipes...</p>
        </LoadingContainer>
      </Container>
    );
  }

  if (isError) {
    return (
      <Container>
        <ErrorContainer>
          <h2>Error loading recipes</h2>
          <p>{(error as Error)?.message || 'An unknown error occurred'}</p>
        </ErrorContainer>
      </Container>
    );
  }

  return (
    <Container>
      <Content>
        <Header>
          <Title>Emil & Lucia's {data?.items.length || 0} recipes</Title>
        </Header>

        <WeeknightRecipes 
          recipes={weeknightRecipes} 
          onRecipeClick={handleRecipeClick} 
        />

        <SpecialOccasionRecipes 
          recipes={specialOccasionRecipes} 
          onRecipeClick={handleRecipeClick} 
        />

        <DessertRecipes 
          recipes={dessertRecipes} 
          onRecipeClick={handleRecipeClick} 
        />
      </Content>
    </Container>
  );
};

export default RecipesScreen;
