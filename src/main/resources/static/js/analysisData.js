/**
 *@author  le
 *date:    2020/12/23
 *describe：
 */

// 鼠标悬浮在小图片上显示大图片的功能
// 先隐藏大图片
$(".bigImg").each(
    function () {
        $(this).hide();
    }
)
//给小图片所在的td绑定上函数
$(".imgTd").each(
    function () {
        this.onmousemove = function (e) {
            var bigImg = $(this).find(".bigImg")[0]
            bigImg.style.left = "150px"
            bigImg.style.bottom = "60px"
            $(bigImg).show();
        }
        $(this).mouseout(function () {
            $(this).find(".bigImg").hide();
        })
    }
)
//设置导航栏的状态
$("a[href='listUser']").removeClass("active")
$("a[href='listProduct']").removeClass("active")
$("a[href='analysisData']").addClass("active")

//商品访问统计表
// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('productVisits'));
let option = {
    title: {
        text: ''
    },

    legend: {
        orient: 'vertical',
        x: 'left',
        data: []
    },
    series: [
        {
            name: '访问来源',
            type: 'pie',    // 设置图表类型为饼图
            radius: '55%',  // 饼图的半径，外半径为可视区尺寸（容器高宽中较小一项）的 55% 长度。
            center: ['50%', '60%'],
            data: [














            ]          // 数据数组，name 为数据项名称，value 为数据项值
        }
    ]
}
//为pie图传入数据
let pieDataObj = {}
$(".productName").each(function (index, element) {
    // 对象的键都是字符串，比如"name"
    option.series[0].data[index] = {};
    option.series[0].data[index]["name"] = $(this).text()
    // console.log(option.series[0].data[index])
    option.legend.data.push($(this).text())
})
$(".productVisit").each(function (index, element) {
    option.series[0].data[index]["value"] = $(this).text()
})
myChart.setOption(option)

// 创建热点商品条形图
var barChart = echarts.init(document.getElementById('hotProduct'));
//bar图传入数据
var baroption = {
    title: {
        text: ''
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['访问量', '库存']
    },
    grid: {
        left:50,
        x: 30,
        x2: 40,
        y2: 24
    },
    calculable: true,
    xAxis: [{
        axisLabel: {
            interval:0,
            rotate:0
        },
        data: []
    }],
    yAxis: [{
        type: 'value'
    }],
    series: [{
        name: '访问量',
        type: 'bar',
        data: []
    }, {
        name: '库存',
        type: 'bar',
        data: []
    }]
};
$(".productName2").each(function (index, element) {
    // 对象的键都是字符串，比如"name"
    baroption.xAxis[0].data.push($(this).text())
})
$(".visitCount2").each(function (index, element) {
    baroption.series[0].data.push($(this).text())
    console.log($(this).text())
})
$(".sum2").each(function (index, element) {
    baroption.series[1].data.push($(this).text())
})
barChart.setOption(baroption)

