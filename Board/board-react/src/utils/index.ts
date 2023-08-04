import { ThemeProvider, createTheme } from "@mui/material/styles";
import React, { useEffect, useRef } from "react";

export const darkTheme = createTheme({
    palette: {
      mode: 'dark',
    },
  });
  
  