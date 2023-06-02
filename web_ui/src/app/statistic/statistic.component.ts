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
  basket_info: StatData[] = [];
  profit_info: StatData[] = [];

  sum_users: number = 0;
  sum_orders: number = 0;
  sum_basket: number = 0;
  sum_profit: number = 0;

  line_chart_users: EChartsOption = {} ;
  line_chart_orders: EChartsOption = {} ;
  histo_chart_colors: EChartsOption = {} ;
  line_chart_basket: EChartsOption = {} ;
  line_chart_profit: EChartsOption = {} ;


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
      ({ users, orders, colors, basket, profit }: { users: any; orders: any; colors: any; basket: any; profit: any }) => {
        // Vider les tableaux stat_info et orders_info avant de charger de nouvelles données
        this.users_info = [];
        this.orders_info = [];
        this.colors_info = [];
        this.basket_info = [];
        this.profit_info = [];

        this.sum_users = 0;
        this.sum_orders = 0;
        this.sum_basket = 0;
        this.sum_profit = 0;
        // Convertir les données JSON en un tableau de paires clé-valeur et stocker les données dans les tableaux
        const usersKeyValueArray = Object.entries(users);
        const ordersKeyValueArray = Object.entries(orders);
        const colorsKeyValueArray = Object.entries(colors);
        const basketKeyValueArray = Object.entries(basket);
        const profitKeyValueArray = Object.entries(profit);

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
        basketKeyValueArray.sort(sortFunction);
        profitKeyValueArray.sort(sortFunction);

        usersKeyValueArray.forEach(([key, value]) => {
          this.users_info.push({ key, value: value as number });
        });

        ordersKeyValueArray.forEach(([key, value]) => {
          this.orders_info.push({ key, value: value as number });
        });

        colorsKeyValueArray.forEach(([key, value]) => {
          this.colors_info.push({ key, value: value as number });
        });

        basketKeyValueArray.forEach(([key, value]) => {
          this.basket_info.push({ key, value: value as number });
        });

        profitKeyValueArray.forEach(([key, value]) => {
          this.profit_info.push({ key, value: value as number });
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
        this.histo_chart_colors = this.createPieChartOption(colors_keys, colors_values, colors_keys);

        const basket_keys = this.basket_info.map(x => x.key);
        const basket_values = this.basket_info.map(x => x.value);
        this.sum_basket = basket_values.reduce((acc, val) => acc + val, 0);
        // this.line_chart_basket = this.createLineChartOption(basket_keys, basket_values);

        const profit_keys = this.profit_info.map(x => x.key);
        const profit_values = this.profit_info.map(x => x.value);
        this.sum_profit = profit_values.reduce((acc, val) => acc + val, 0);
        // this.line_chart_profit = this.createLineChartOption(profit_keys, profit_values);

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
          color: 'rgba(0, 0, 0, 0.3)'
        },
        lineStyle: {
          color: 'rgb(0, 0, 0)'
        },
        itemStyle: {
          color: 'rgb(0, 0, 0)'
        }
      }]
    };
  }


  createPieChartOption(xData: string[], seriesData: number[], colors: string[]): EChartsOption {
    const data = xData.map((name, index) => {
      return { value: seriesData[index], name: name, itemStyle: { color: colors[index] } };
    });

    return {
      tooltip: {
        trigger: 'item'
      },
      legend: {
        top: '1%',
        left: 'center',
      },
      series: [
        {
          type: 'pie',
          radius: ['40%', '80%'],
          avoidLabelOverlap: false,
          itemStyle: {
            borderRadius: 5,
            borderColor: '#ddd',
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
          data: data
        }
      ]
    };
  }

}
