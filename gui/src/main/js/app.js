import React, {StrictMode} from "react";
import { createRoot } from 'react-dom/client';

import HomePage from "./components/HomePage";

const container = document.getElementById('appContainer');
const root = createRoot(container);
root.render(<HomePage />);
