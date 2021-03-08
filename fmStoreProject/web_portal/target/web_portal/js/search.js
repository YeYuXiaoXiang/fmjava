new Vue({
    el:"#app",
    data:{
        searchMap:{
            'keywords':'',  //搜索关键字
            'pageNo':1,     //当前页
            'pageSize':5    //每页展示多少条数据
        },
        resultMap:{
            rows:[],
            total:0,
            totalPages:0
        },
        pageLabel:[],//所有页码
        firstDot:true,//前面有点
        lastDot:true //后边有点
    },
    methods:{
        getQueryString:function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
            var r = window.location.search.substr(1).match(reg);
            if (r!=null){
                return (decodeURI(r[2]));
            }
            return null;
        },
        search:function () {
            this.searchMap.pageNo= parseInt(this.searchMap.pageNo);//转换为数字
            var _this = this;
            axios.post("/itemsearch/search.do",this.searchMap)
                .then(function (response) {
                    _this.resultMap = response.data;
                    _this.buildPages(_this);
                }).catch(function (reason) {
                console.log(reason.data);
            });
        },
        buildPages:function (obj) {
            obj.pageLabel = [];
            var firstPage = 1;
            var lastPage = obj.resultMap.totalPages;
            obj.firstDot = true;
            obj.lastDot = true;

            if (obj.resultMap.totalPages > 5) {

                if (obj.searchMap.pageNo <= 3){
                    obj.firstDot = false;
                    lastPage = 5;
                }else if(obj.searchMap.pageNo > obj.resultMap.totalPages - 2) {
                    obj.lastDot = false;
                    firstPage = obj.resultMap.totalPages - 4;
                }else{
                    firstPage = obj.searchMap.pageNo - 2;
                    lastPage = obj.searchMap.pageNo + 2;
                }
            }else{
                obj.firstDot = false;
                obj.lastDot = false;
            }
            for (var i = firstPage; i <= lastPage; i++) {
                obj.pageLabel.push(i);
            }
        },
        queryByPage:function (pageNo) {
            if (pageNo < 1 || pageNo > this.resultMap.totalPages){
                return;
            }
            this.searchMap.pageNo = pageNo;
            this.search();
        },
        isTopPage:function () {
            if (this.searchMap.pageNo == 1) {
                return true;
            } else{
                return false;
            }
        },
        isEndPage:function () {
            if (this.searchMap.pageNo == this.resultMap.totalPages) {
                return true;
            } else{
                return false;
            }
        },
        addSearchItem:function (key, value) {
            if (key == 'category' || key == 'brand' || key == 'price') {
                this.searchMap[key] = value;

            }else {
                Vue.set(this.searchMap.spec, key, value);
            }
        },
        openDetailPage:function (goodsId) {
            window.open("http://localhost:8086/"+goodsId+".html");
        }


    },
    watch: { //监听属性的变化

    },
    created: function() {//创建对象时调用

    },
    mounted:function () {//页面加载完
        var sc = this.getQueryString("sc");
        this.searchMap.keywords = sc;
        this.search();
    }
});