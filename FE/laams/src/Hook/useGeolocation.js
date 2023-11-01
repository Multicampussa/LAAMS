import { useCallback } from 'react'

export const useGeoLocation = (options) => {

  const getLocation = useCallback(async (geolocation)=>{
    return new Promise((resolve,rejected)=>{
      geolocation.getCurrentPosition(resolve,rejected);
    });
  },[])

  const getGeolcation = useCallback(async () => {
    const { geolocation } = navigator

    if (!geolocation) {
      setError('Geolocation is not supported.')
      return
    }

    if(!options) options = {};
    try{
      let res = await getLocation(geolocation);
      if(res){
        if(res.coords){
          return {latitude : res.coords.latitude, longitude : res.coords.longitude};
        }
      }
      return res;
    }catch(err){
      throw new Error(err.message);
    }
  }, [options]);

  return getGeolcation
}