import { Component, AfterViewInit } from '@angular/core';
import type { EChartsOption } from 'echarts';
import { getInstanceByDom, connect } from 'echarts';


@Component({
  selector: 'app-statistic',
  templateUrl: './statistic.component.html',
  styleUrls: ['./statistic.component.css']
})


export class StatisticComponent { //implements AfterViewInit

  onTimePeriodChange(event: Event) {
    const selectedValue = (event.target as HTMLSelectElement).value;
    // Mettez à jour vos données en fonction de la valeur sélectionnée
  }



  createHistogramChartOption(xData: string[], seriesData: number[]): EChartsOption {
    return {

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
          data: xData,
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
          data: seriesData,
          itemStyle: {
            color: (params) => {
              const colors = xData;
              return colors[params.dataIndex];
            },
            borderColor: 'grey',
            borderWidth: 0.5,
          },

        }
      ],
    };
  }


  createLineChartOption(xData: string[], seriesData: number[]): EChartsOption {
    return {
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: xData
      },
      yAxis: {
        type: 'value'
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow',
        },
        backgroundColor: 'beige'
      },
      series: [{
        data: seriesData,
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
    };
  }

  histogramChartOption = this.createHistogramChartOption(['Black', 'Blue', 'Green', 'Grey', 'Red', 'White', 'Yellow'], [10, 52, 200, 334, 390, 330, 220]);
  lineChartOption1 = this.createLineChartOption(['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'], [820, 932, 901, 934, 1290, 1430, 1550, 1600, 1650.1450, 1680.1890]);
  lineChartOption2 = this.createLineChartOption(['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'], [20, 32, 101, 234, 290, 430, 556, 600, 650.1450, 680.1890]);
  lineChartOption3 = this.createLineChartOption(['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'], [8, 31, 71, 80, 82, 90, 120, 234, 300, 400]);

  constructor() { }

}





  // ngAfterViewInit() {
  //   setTimeout(() => {
  //     const chartElement1 = document.getElementById('chart1')!;
  //     const chartElement2 = document.getElementById('chart2')!;
  //     const chart1 = getInstanceByDom(chartElement1)!;
  //     const chart2 = getInstanceByDom(chartElement2)!;
  //     // connect([chart1, chart2]);
  //   });
  // }
