export const setAccessToken = (payload) => ({
  type: "accessToken",
  payload
});

export const setAccessTokenExpireTime = (payload) => ({
  type: "accessTokenExpireTime",
  payload
});

export const setAuthority = (payload) => ({
  type: "authority",
  payload
});