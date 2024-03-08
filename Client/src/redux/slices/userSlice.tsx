/* eslint-disable @typescript-eslint/no-unused-vars */
import { createSlice } from "@reduxjs/toolkit";
import { AuthUser } from "../../types/types";
import { AppDispatch } from "..";
import authService from "../../services/auth.service";

const initialState: AuthUser = {
  token: undefined,
  username: undefined,
};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    loginUser(state, action) {
      return action.payload;
    },
    logoutUser(state) {
      return initialState;
    },
  },
});

export const { loginUser, logoutUser } = userSlice.actions;

export default userSlice.reducer;

export const login = (credentials: any) => async (dispatch: AppDispatch) => {
  const token = await authService.loginWithCode(credentials);
  await dispatch(loginUser(token));
};

export const logout = () => async (dispatch: AppDispatch) => {
  await dispatch(logoutUser());
};
