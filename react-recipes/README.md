# Recipes App - React Version

This is a React implementation of the Recipes application, originally built with Kotlin and Compose. The application allows users to browse recipes, view recipe details, and use a cooking mode to follow recipe instructions step by step.

## Features

- Browse recipes by category (Weeknight, Special Occasion, Desserts)
- View detailed recipe information including ingredients and instructions
- Adjust serving sizes
- Cooking mode for step-by-step instruction following
- Responsive design that works on mobile and desktop
- Light and dark theme support

## Prerequisites

Before you begin, ensure you have the following installed:
- [Node.js](https://nodejs.org/) (v18 or higher recommended)
- npm (comes with Node.js) or [yarn](https://yarnpkg.com/)

## Getting Started

Follow these steps to run the application locally:

### 1. Navigate to the project directory

```bash
cd react-recipes
```

### 2. Install dependencies

```bash
npm install
# or if you use yarn
yarn
```

### 3. Start the development server

```bash
npm run dev
# or if you use yarn
yarn dev
```

This will start the development server and open the application in your default browser. If it doesn't open automatically, you can access it at [http://localhost:5173](http://localhost:5173).

## Available Scripts

In the project directory, you can run:

- `npm run dev` - Starts the development server
- `npm run build` - Builds the app for production
- `npm run lint` - Runs the linter to check for code issues
- `npm run preview` - Previews the production build locally

## Technologies Used

- [React](https://reactjs.org/) - UI library
- [TypeScript](https://www.typescriptlang.org/) - Type safety
- [Vite](https://vitejs.dev/) - Build tool and development server
- [React Router](https://reactrouter.com/) - Navigation and routing
- [React Query](https://tanstack.com/query/latest) - Data fetching and caching
- [Styled Components](https://styled-components.com/) - Styling
- [Axios](https://axios-http.com/) - HTTP client
