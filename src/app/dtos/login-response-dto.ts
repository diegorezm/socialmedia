import { User } from "../models/user.model";

export type TokenResponseDto = {
  token: string;
  expiresAt: string;
};

export interface LoginResponseDto {
  token: TokenResponseDto;
  user: User;
}