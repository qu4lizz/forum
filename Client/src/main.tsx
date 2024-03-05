import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./index.css";
import "@mantine/core/styles.css";
import "@mantine/notifications/styles.css";
import { store } from "./redux/index.tsx";
import { Provider } from "react-redux";
import { MantineProvider } from "@mantine/core";
import { BrowserRouter } from "react-router-dom";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

root.render(
  <React.StrictMode>
    <Provider store={store}>
      <MantineProvider defaultColorScheme="dark">
        <BrowserRouter>
          <App />
        </BrowserRouter>
      </MantineProvider>
    </Provider>
  </React.StrictMode>
);
