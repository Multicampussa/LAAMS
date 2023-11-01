import React from 'react';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Bar } from 'react-chartjs-2';
import useCenter from './../../../Hook/useCenter';
import { useMemo } from 'react';
import { useState } from 'react';
import { useCallback } from 'react';

const Chart = () => {
  const centerData = useCenter();
  const [chartTitle,setChartTitle] = useState("센터 별 시험 횟수");
  const [selectOpen,setSelectOpen] = useState(false);
  const [region,setRegion] = useState("서울");
  const [regionSelectOpen,setRegionSelectOpen] = useState(false);

  const handleChartRegionItem = useCallback((region)=>{
    setRegion(region);
    setRegionSelectOpen(false);
  },[]);

  const handleChartItem = useCallback((title)=>{
    setChartTitle(title);
    setSelectOpen(false);
  },[]);

  const regionItem = useMemo(()=>{
    if(!centerData) return [];
    return Object.keys(centerData).reverse().map((e,idx)=><li className='manager-chart-item' onClick={()=>handleChartRegionItem(e)} key={idx}>{e}</li>);
  })
  const chartItem = useMemo(()=>{
    const chartList = [
      "센터 별 시험 횟수",
      "센터 별 보상 건 수",
      "센터 별 응시자 수",
      "일년간 에러리포트 수 변화",
      "일년간 보상요청 수 변화"
    ]
    return chartList.map((e,idx)=>
    <li className='manager-chart-item' key={idx} onClick={()=>handleChartItem(e)}>{e}</li>);
  },[handleChartItem]);

  const datasets = useMemo(()=>{
    if(!centerData || !region) return [];
    const temp = centerData[region].map(e=>{
      return {
        label: e.centerName,
        data: [Math.floor(Math.random()*(1000))],
        backgroundColor: `rgba(${Math.floor(Math.random()*(255))},${Math.floor(Math.random()*(255))}, ${Math.floor(Math.random()*(255))}, 0.5)`,
      };
    });
    console.log(temp);
    return temp;
  },[centerData,region])

  ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
  );
  
  const options = {
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {//차트 제목
        display: false,
        text: chartTitle,
      },
    },
  };
  
  const data = {
    labels:[chartTitle],
    datasets: datasets,
  };
  
  return (
    <section className='manager-chart'>
      <div className='flex-row'>
        <div className='flex-1'>
          <div className='manager-chart-select-title' onClick={()=>setSelectOpen(!selectOpen)}>{chartTitle}</div>
          <div className='manager-chart-select'>
            <ul className={`manager-chart-select-${selectOpen}`}>
              {
                chartItem
              }
            </ul>
          </div>
        </div>
        <div className='flex-1'>
            <div className='manager-chart-select-title' onClick={()=>setRegionSelectOpen(!regionSelectOpen)}>{region}</div>
            <div className='manager-chart-select'>
              <ul className={`manager-chart-select-${regionSelectOpen}`}>
                {
                  regionItem
                }
              </ul>
            </div>
          </div>
      </div>
      <div className='manager-chart-box'>
        <Bar options={options} data={data} />
      </div>
  </section>
  )
}

export default Chart