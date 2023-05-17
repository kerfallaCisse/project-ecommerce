import { Component, AfterViewInit, OnInit } from '@angular/core';
import type { EChartsOption } from 'echarts';
import { getInstanceByDom, connect } from 'echarts';
import { StatisticService } from '../services/statistic/statistic.service';
import { StatData } from '../shared/models/statistic';



@Component({
  selector: 'app-statistic',
  templateUrl: './statistic.component.html',
  styleUrls: ['./statistic.component.css'],
  providers: [StatisticService]
})


export class StatisticComponent implements OnInit{ //implements AfterViewInit

  constructor(private statService: StatisticService) { }

  users_info: StatData[] = [];
  orders_info: StatData[] = [];
  colors_info:  StatData[] = [];

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

        // Convertir les données JSON en un tableau de paires clé-valeur et stocker les données dans les tableaux
        const usersKeyValueArray = Object.entries(users);
        const ordersKeyValueArray = Object.entries(orders);
        const colorsKeyValueArray = Object.entries(colors);

        // Trier les tableaux par clé, sauf pour les jours de la semaine
        const weekdays = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'];
        usersKeyValueArray.sort((a, b) => {
          if (weekdays.includes(a[0]) && weekdays.includes(b[0])) {
            return 0;
          } else {
            return a[0].localeCompare(b[0]);
          }
        });
        ordersKeyValueArray.sort((a, b) => {
          if (weekdays.includes(a[0]) && weekdays.includes(b[0])) {
            return 0;
          } else {
            return a[0].localeCompare(b[0]);
          }
        });
        colorsKeyValueArray.sort((a, b) => {
          if (weekdays.includes(a[0]) && weekdays.includes(b[0])) {
            return 0;
          } else {
            return a[0].localeCompare(b[0]);
          }
        });

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
        this.line_chart_users = this.createLineChartOption(users_keys, users_values);

        const orders_keys = this.orders_info.map(x => x.key);
        const orders_values = this.orders_info.map(x => x.value);
        this.line_chart_orders = this.createLineChartOption(orders_keys, orders_values);

        const colors_keys = this.colors_info.map(x => x.key);
        const colors_values = this.colors_info.map(x => x.value);
        this.histo_chart_colors = this.createHistogramChartOption(colors_keys, colors_values);

      },
      error => {
        console.log("Erreur lors de la réception des données");
      },
      () => {
        console.log("Stat info:", this.users_info);
        console.log("Orders info:", this.orders_info);
        console.log("Colors info:", this.colors_info);
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

  // histogramChartOption = this.createHistogramChartOption(['Black', 'Blue', 'Green', 'Grey', 'Red', 'White', 'Yellow'], [10, 52, 200, 334, 390, 330, 220]);
  lineChartOption1 = this.createLineChartOption(['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'], [820, 932, 901, 934, 1290, 1430, 1550, 1600, 1650.1450, 1680.1890]);
  // lineChartOption2 = this.createLineChartOption(['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'], [20, 32, 101, 234, 290, 430, 556, 600, 650.1450, 680.1890]);
  // lineChartOption3 = this.createLineChartOption(['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'], [8, 31, 71, 80, 82, 90, 120, 234, 300, 400]);


}
