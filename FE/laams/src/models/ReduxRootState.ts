
export interface RootState {
    User: {
      accessToken: string;
      accessTokenExpireTime: string;
      authority: string;
      memberId: string;
      userClear: boolean;
    };
  }