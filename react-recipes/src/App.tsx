import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { RecipesThemeProvider, GlobalStyles } from './theme';
import { RecipesScreen, RecipeDetailScreen } from './screens';

// Create a client for React Query
const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false,
      staleTime: 5 * 60 * 1000, // 5 minutes
    },
  },
});

const App: React.FC = () => {
  return (
    <QueryClientProvider client={queryClient}>
      <RecipesThemeProvider>
        <GlobalStyles />
        <Router>
          <Routes>
            <Route path="/" element={<RecipesScreen />} />
            <Route path="/recipe/:slug" element={<RecipeDetailScreen />} />
          </Routes>
        </Router>
      </RecipesThemeProvider>
    </QueryClientProvider>
  );
};

export default App;
