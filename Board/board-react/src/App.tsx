import React, { useEffect, useState } from 'react';
import logo from './logo.svg';
import axios from "axios";
import './App.css';
import SignUp from './views/Authentication/SignUp';
import Athentication from './views/Authentication';
import MainLayout from './views/layouts/MainLayout';

export default function App() {
 
  return (
    <MainLayout />
  );
}