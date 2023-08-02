import React, { useEffect, useState } from 'react';
import logo from './logo.svg';
import axios from "axios";
import './App.css';
import SignUp from './views/Authentication/SignUp';
import Athentication from './views/Authentication';
import MainLayout from './views/layouts/MainLayout';
import { ThemeProvider } from '@emotion/react';
import { darkTheme } from './utils';

export default function App() {
 
  return (
   
    <MainLayout />
  );
}