import { useCallback } from 'react'
import useApi from './useApi';

const useCreateRoom = () => {
  const api = useApi();
  const createRoom = useCallback(async()=>{
    await api.post("chat/room");
  },[api])
  return createRoom;
}

export default useCreateRoom