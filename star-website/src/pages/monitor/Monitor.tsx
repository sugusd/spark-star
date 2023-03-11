import React, {useRef} from 'react';
import './Monitor.scss'
//
// const Echarts = (_rawData: any) => {
//
//     const chartRef = useRef();
//
//     const option = {
//         dataset: [
//             {
//                 id: 'dataset_raw',
//                 source: _rawData
//             },
//             {
//                 id: 'dataset_since_1950_of_germany',
//                 fromDatasetId: 'dataset_raw',
//                 transform: {
//                     type: 'filter',
//                     config: {
//                         and: [
//                             {dimension: 'Year', gte: 1950},
//                             {dimension: 'Country', '=': 'Germany'}
//                         ]
//                     }
//                 }
//             },
//             {
//                 id: 'dataset_since_1950_of_france',
//                 fromDatasetId: 'dataset_raw',
//                 transform: {
//                     type: 'filter',
//                     config: {
//                         and: [
//                             {dimension: 'Year', gte: 1950},
//                             {dimension: 'Country', '=': 'France'}
//                         ]
//                     }
//                 }
//             }
//         ],
//         title: {
//             text: 'Income of Germany and France since 1950'
//         },
//         tooltip: {
//             trigger: 'axis'
//         },
//         xAxis: {
//             type: 'category',
//             nameLocation: 'middle'
//         },
//         yAxis: {
//             name: 'Income'
//         },
//         series: [
//             {
//                 type: 'line',
//                 datasetId: 'dataset_since_1950_of_germany',
//                 showSymbol: false,
//                 encode: {
//                     x: 'Year',
//                     y: 'Income',
//                     itemName: 'Year',
//                     tooltip: ['Income']
//                 }
//             },
//             {
//                 type: 'line',
//                 datasetId: 'dataset_since_1950_of_france',
//                 showSymbol: false,
//                 encode: {
//                     x: 'Year',
//                     y: 'Income',
//                     itemName: 'Year',
//                     tooltip: ['Income']
//                 }
//             }
//         ]
//     };
// };
function Monitor() {



    return <>
        <div>健康中心</div>
    </>;
}

export default Monitor;
