export interface AuthenticationResponse {
  accessToken: string,
  user: {
    id: number,
    email: string
  }
}
