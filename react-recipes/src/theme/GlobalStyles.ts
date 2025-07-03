import { createGlobalStyle } from 'styled-components';
import type { Theme } from './ThemeProvider';

export const GlobalStyles = createGlobalStyle<{ theme: Theme }>`
  * {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
  }

  html, body, #root {
    height: 100%;
    width: 100%;
  }

  body {
    font-family: 'Inter', sans-serif;
    background-color: ${({ theme }) => theme.colors.backgroundPage};
    color: ${({ theme }) => theme.colors.foregroundDefault};
    transition: background-color 0.2s ease-in-out, color 0.2s ease-in-out;
  }

  a {
    color: ${({ theme }) => theme.colors.linkDefault};
    text-decoration: none;

    &:hover {
      color: ${({ theme }) => theme.colors.linkHover};
    }

    &:active {
      color: ${({ theme }) => theme.colors.linkPressed};
    }
  }

  button {
    cursor: pointer;
    border: none;
    background: none;
    font-family: inherit;

    &:disabled {
      cursor: not-allowed;
      opacity: 0.6;
    }
  }

  img {
    max-width: 100%;
    height: auto;
  }

  h1 {
    ${({ theme }) => theme.typography.h1}
  }

  h2 {
    ${({ theme }) => theme.typography.h2}
  }

  h3 {
    ${({ theme }) => theme.typography.h3}
  }

  h4 {
    ${({ theme }) => theme.typography.h4}
  }

  h5 {
    ${({ theme }) => theme.typography.h5}
  }

  h6 {
    ${({ theme }) => theme.typography.h6}
  }

  p {
    ${({ theme }) => theme.typography.body1}
  }
`;
