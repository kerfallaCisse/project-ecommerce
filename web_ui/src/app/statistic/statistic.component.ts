import { Component, OnInit } from '@angular/core';
import type { EChartsOption } from 'echarts';
import { StatisticService } from '../services/statistic/statistic.service';
import { StatData } from '../shared/models/statistic';



@Component({
  selector: 'app-statistic',
  templateUrl: './statistic.component.html',
  styleUrls: ['./statistic.component.css'],
  providers: [StatisticService]
})


export class StatisticComponent implements OnInit{

  constructor(private statService: StatisticService) { }

  users_info: StatData[] = [];
  orders_info: StatData[] = [];
  colors_info: StatData[] = [];

  sum_users: number = 0;
  sum_orders: number = 0;

  line_chart_users: EChartsOption = {} ;
  line_chart_orders: EChartsOption = {} ;
  histo_chart_colors: EChartsOption = {} ;



  ngOnInit(): void {
    // Charger les données initiales en utilisant la valeur par défaut 'last_week'
    this.loadStatData('last_week');
  }

  onTimePeriodChange(event: Event) {
    const selectedValue = (event.target as HTMLSelectElement).value;
    // Charger les données en fonction de la valeur sélectionnée
    this.loadStatData(selectedValue);
  }

  // Créer une méthode loadStatData() pour charger les données en fonction de la valeur sélectionnée
  loadStatData(selectedValue: string): void {
    this.statService.getEndpointURLs(selectedValue).subscribe(
      ({ users, orders, colors }: { users: any; orders: any; colors: any }) => {
        // Vider les tableaux stat_info et orders_info avant de charger de nouvelles données
        this.users_info = [];
        this.orders_info = [];
        this.colors_info = [];

        this.sum_users = 0;
        this.sum_orders = 0;
        // Convertir les données JSON en un tableau de paires clé-valeur et stocker les données dans les tableaux
        const usersKeyValueArray = Object.entries(users);
        const ordersKeyValueArray = Object.entries(orders);
        const colorsKeyValueArray = Object.entries(colors);

        // Trier les tableaux par clé, sauf pour les jours de la semaine
        const weekdays = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'];
        const monthRegex = /^month(\d+)$/;
        const weekRegex = /^week(\d+)$/;

        const sortFunction = (a: any[], b: any[]) => {
          if (weekdays.includes(a[0]) && weekdays.includes(b[0])) {
            return 0;
          } else if (monthRegex.test(a[0]) && monthRegex.test(b[0])) {
            const monthA = parseInt(a[0].match(monthRegex)[1]);
            const monthB = parseInt(b[0].match(monthRegex)[1]);
            return monthA - monthB;
          } else if (weekRegex.test(a[0]) && weekRegex.test(b[0])) {
            const weekA = parseInt(a[0].match(weekRegex)[1]);
            const weekB = parseInt(b[0].match(weekRegex)[1]);
            return weekA - weekB;
          } else {
            return a[0].localeCompare(b[0]);
          }
        };

        usersKeyValueArray.sort(sortFunction);
        ordersKeyValueArray.sort(sortFunction);
        colorsKeyValueArray.sort(sortFunction);

        usersKeyValueArray.sort(sortFunction);
        ordersKeyValueArray.sort(sortFunction);
        colorsKeyValueArray.sort(sortFunction);

        usersKeyValueArray.forEach(([key, value]) => {
          this.users_info.push({ key, value: value as number });
        });

        ordersKeyValueArray.forEach(([key, value]) => {
          this.orders_info.push({ key, value: value as number });
        });

        colorsKeyValueArray.forEach(([key, value]) => {
          this.colors_info.push({ key, value: value as number });
        });

        const users_keys = this.users_info.map(x => x.key);
        const users_values = this.users_info.map(x => x.value);
        this.sum_users = users_values.reduce((acc, val) => acc + val, 0);
        this.line_chart_users = this.createLineChartOption(users_keys, users_values);

        const orders_keys = this.orders_info.map(x => x.key);
        const orders_values = this.orders_info.map(x => x.value);
        this.sum_orders = orders_values.reduce((acc, val) => acc + val, 0);
        this.line_chart_orders = this.createLineChartOption(orders_keys, orders_values);

        const colors_keys = this.colors_info.map(x => x.key);
        const colors_values = this.colors_info.map(x => x.value);
        this.histo_chart_colors = this.createHistogramChartOption(colors_keys, colors_values);

      },
      error => {
        console.log("Erreur lors de la réception des données");
      },
      () => {
        console.log("Stat info:", this.sum_users);
        console.log("Orders info:", this.sum_orders);
      }
    );
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
          color: 'rgba(76, 87, 123, 0.5)'
        },
        lineStyle: {
          color: 'rgb(76, 87, 123)'
        },
        itemStyle: {
          color: 'rgb(76, 87, 123)'
        }
      }]
    };
  }


  createPieChartOption(xData: string[], seriesData: number[]): EChartsOption {
    return {
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: '5%',
      left: 'center'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 40,
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: [
          { value: 1048, name: 'Search Engine' },
          { value: 735, name: 'Direct' },
          { value: 580, name: 'Email' },
          { value: 484, name: 'Union Ads' },
          { value: 300, name: 'Video Ads' }
        ]
      }
    ]
  };
}

  // histogramChartOption = this.createHistogramChartOption(['Black', 'Blue', 'Green', 'Grey', 'Red', 'White', 'Yellow'], [10, 52, 200, 334, 390, 330, 220]);
  lineChartOption1 = this.createLineChartOption(['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'], [820, 932, 901, 934, 1290, 1430, 1550, 1600, 1650.1450, 1680.1890]);
  // lineChartOption2 = this.createLineChartOption(['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'], [20, 32, 101, 234, 290, 430, 556, 600, 650.1450, 680.1890]);
  // lineChartOption3 = this.createLineChartOption(['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'], [8, 31, 71, 80, 82, 90, 120, 234, 300, 400]);


}
