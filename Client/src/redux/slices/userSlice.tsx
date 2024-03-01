import { createSlice } from "@reduxjs/toolkit";
import { AuthUser } from "../../types/types";

const initialState: AuthUser = {
  token: undefined,
};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    initUser(state, action) {
      return action.payload;
    },
    loginUser(state, action) {
      return action.payload;
    },
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    logoutUser(state) {
      return undefined;
    },
  },
});

export const { initUser, loginUser, logoutUser } = userSlice.actions;

export default userSlice.reducer;
