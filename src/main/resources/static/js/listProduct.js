/**
 *@author  le
 *date:    2020/12/23
 *describe：
 */
// 批量删除功能
$("#btnBatchDelete").click(function () {
    var ids = new Array();
    $(".batchDelete:checked").each(
        function () {
            var id = $(this).attr("id");
            // 调用数组对象的push()方法将数据存入数组
            ids.push(id)
        });
    console.log("batchDelete---" + ids)
    // 用ajax有一个缺点：后台的return "redirect:listProduct";会失效
    //要用window.location.href 跳转页面
    $.post({
        url: "batchDelete",
        data: {
            ids: ids
        },
        async: false,
        success: function () {
            window.location.href = "listProduct";
            console.log("success")
            // 删除成功就跳转到商品列表
        }
    })
})
// 重置导航栏的激活状态
$("a[href='listProduct']").addClass("active")
$("a[href='listUser']").removeClass("active")
$("a[href='analysisData']").removeClass("active")

// 访问趋势
// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('salesTrend'));
// 指定图表的配置项和数据
var option = {
    title: {
        text: '电商平台销售趋势'
    },
    tooltip: {},
    legend: {
        data: ['访问量']
    },
    xAxis: {
        // data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
        data: []
    },
    yAxis: {},
    series: [{
        name: '访问量',
        type: 'bar',
        // data: [5, 20, 36, 10, 10, 20]
        data: []
    }]
};
option.title.text = '电商平台访问趋势';
option.legend.data = ["访问量"];
// option.xAxis.data = ["衬衫2", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"];
option.series[name] = '访问量'
option.series[0].type = 'line'
// option.series[0].data = [5, 20, 36, 10, 10, 20]
//为折线图传入xy轴的数据
$(".productName").each(function () {
    option.xAxis.data.push($(this).text())
})
$(".productVisit").each(function () {
    option.series[0].data.push($(this).text())
})
// 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);
