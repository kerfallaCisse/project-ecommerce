import { Component, AfterViewInit } from '@angular/core';
import type { EChartsOption } from 'echarts';
import { getInstanceByDom, connect } from 'echarts';


@Component({
  selector: 'app-statistic',
  templateUrl: './statistic.component.html',
  styleUrls: ['./statistic.component.css']
})


export class StatisticComponent  implements AfterViewInit {


  histogramOption : EChartsOption = {
    color: ['#3398DB'],
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow',
      },
      backgroundColor : 'beige'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true,
    },
    xAxis: [
      {
        type: 'category',
        data: ['Black', 'Blue', 'Green', 'Grey', 'Red', 'White', 'Yellow'],
        axisTick: {
          alignWithLabel: true,
        },
      },
    ],
    yAxis: [
      {
        type: 'value',
      },
    ],
    series: [
      {
        name: 'Counters',
        type: 'bar',
        barWidth: '20%',
        data: [10, 52, 200, 334, 390, 330, 220],
        itemStyle: {
          color: (params) => {
            const colors = ['black', 'blue', 'green', 'grey', 'red', 'white', 'yellow'];
            return colors[params.dataIndex];
          },
        },
      }
    ],
  };



  lineChartOption: EChartsOption = {
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
    },
    yAxis: {
      type: 'value'
    },
    // tooltip: {
    //   trigger: 'item',
    //   showDelay: 0,
    //   transitionDuration: 0.2,
    //   formatter: function (params) {
    //     return `<b>${params['name']}</b> : $ ${params['value']}`;
    //   }
    // },
    series: [{
      data: [820, 932, 901, 934, 1290, 1430, 1550, 1600, 1650.1450, 1680.1890],
      type: 'line',
      areaStyle: {
        color: 'rgba(0, 0, 0, 0.5)'
      },
      lineStyle: {
        color: 'black'
      },
      itemStyle: {
        color: 'black'
      }
    }]
  }





  constructor() { }

  ngAfterViewInit() {
    setTimeout(() => {
      const chartElement1 = document.getElementById('chart1')!;
      const chartElement2 = document.getElementById('chart2')!;
      const chart1 = getInstanceByDom(chartElement1)!;
      const chart2 = getInstanceByDom(chartElement2)!;
      connect([chart1, chart2]);
    });
  }
}
