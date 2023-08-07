import { ThemeProvider } from "@emotion/react";
import "./App.css";
import MainLayout from "./views/layouts/MainLayout";
import theme from "./theme/theme";

export default function App() {
  return (
    <ThemeProvider theme = {theme}>
      <MainLayout />
    </ThemeProvider>
  );
}
