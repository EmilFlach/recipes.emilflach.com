import React, { createContext, useContext, useState, useEffect } from 'react';
import { ThemeProvider as StyledThemeProvider } from 'styled-components';
import { LightTheme, DarkTheme } from './colors';
import type { RecipesColors } from './colors';
import { typography } from './typography';

// Define the theme type
export interface Theme {
  colors: RecipesColors;
  typography: typeof typography;
}

// Create the theme context
const ThemeContext = createContext<{
  theme: Theme;
  toggleTheme: () => void;
}>({
  theme: { colors: LightTheme, typography },
  toggleTheme: () => {},
});

// Hook to use the theme
export const useTheme = () => useContext(ThemeContext);

// Theme provider component
export const RecipesThemeProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  // Check if user prefers dark mode
  const prefersDarkMode = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;

  // State to track current theme
  const [isDarkMode, setIsDarkMode] = useState(prefersDarkMode);

  // Create theme object
  const theme: Theme = {
    colors: isDarkMode ? DarkTheme : LightTheme,
    typography,
  };

  // Function to toggle theme
  const toggleTheme = () => {
    setIsDarkMode(!isDarkMode);
  };

  // Listen for changes in system theme preference
  useEffect(() => {
    const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)');

    const handleChange = (e: MediaQueryListEvent) => {
      setIsDarkMode(e.matches);
    };

    mediaQuery.addEventListener('change', handleChange);

    return () => {
      mediaQuery.removeEventListener('change', handleChange);
    };
  }, []);

  return (
    <ThemeContext.Provider value={{ theme, toggleTheme }}>
      <StyledThemeProvider theme={theme}>
        {children}
      </StyledThemeProvider>
    </ThemeContext.Provider>
  );
};
