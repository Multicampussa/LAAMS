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

const Chart = ({title,region}) => {
  const centerData = useCenter();
  const labels = useMemo(()=>{
    if(!centerData || !region) return [];
    const res = [];
    centerData[region].forEach(e=>res.push(e.centerName));
    return res;
  },[centerData,region]);

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
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {//차트 제목
        display: false,
        text: title,
      },
    },
  };
  
  const data = {
    labels:[title],
    datasets: datasets,
  };
  return (
    <div className='manager-chart'>
      <Bar options={options} data={data} />
    </div>
  )
}

export default Chart