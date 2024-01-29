
export interface RootState {
    User: {
      accessToken: string | null;
      accessTokenExpireTime: string | null;
      authority: string | null;
      memberId: string | null;
      userClear: boolean | null;
    };
  }