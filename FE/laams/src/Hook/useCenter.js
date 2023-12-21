import { useCallback, useEffect,useState } from "react";
import useApi from "./useApi.js";

/*
TODO : 센터목록을 받아와 센터 와 지역목록을 분리해 데이터를 가공하여 반환
res : 센터 목록, 지역 목록
*/
const useCenter = () => {
  const [centerData,setCenterData] = useState();
  const api = useApi();
  const getData = useCallback(async ()=>{
    try{
      api.get("manager/centers").then(({data})=>{
        const res = {};
        data.forEach(e=>{
          if(res[e.centerRegion]){
            res[e.centerRegion].push({centerNo:e.centerNo,centerName:e.centerName});
          }else{
            res[e.centerRegion]=[{centerNo:e.centerNo,centerName:e.centerName}];
          }
        });
        setCenterData(res);
      });
    }catch(err){
      console.log(err.response);
    }
  },[api])
  useEffect(()=>{
    getData();
  },[getData]);
  return centerData;
}

export default useCenter