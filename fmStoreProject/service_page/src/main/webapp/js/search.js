new Vue({
    el:"#app",
    data:{
        searchMap:{
            'keywords':'',//搜索关键字
            'category':'',//分类
            'brand':'',//品牌
            'spec':{},//规格
            'price':'',//价格
            'pageNo':1,//当前页
            'pageSize':5,//每页展示多少条数据
            'sort':'',//排序
            'sortField':''//排序的字段
        },
        resultMap:{
            rows:[],
            total:0,
            totalPages:0
        },
        pageLabel:[],//所有页码
        firstDot:true,//前面有点
        lastDot:true,//后边有点
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
                   //构建分页
                   _this.buildPageLabel(_this);
                   console.log(response.data)
                }).catch(function (reason) {
                console.log(reason.data);
            });
        },

        buildPageLabel:function (obj) {
            //构建分页栏
            obj.pageLabel=[];
            var firstPage=1;//开始页码
            var lastPage=obj.resultMap.totalPages;//截止页码
            obj.firstDot=true;//前面有点
            obj.lastDot=true;//后边有点
            if(obj.resultMap.totalPages>5){  //如果页码数量大于5
                if(obj.searchMap.pageNo<=3){//如果当前页码小于等于3 ，显示前5页
                    lastPage=5;
                    obj.firstDot=false;//前面没点
                }else if( obj.searchMap.pageNo>= this.resultMap.totalPages-2 ){//显示后5页
                    firstPage=this.resultMap.totalPages-4;
                    obj.lastDot=false;//后边没点
                }else{  //显示以当前页为中心的5页
                    firstPage=obj.searchMap.pageNo-2;
                    lastPage=obj.searchMap.pageNo+2;
                }
            }else{
                obj.firstDot=false;//前面无点
                obj.lastDot=false;//后边无点
            }
            //构建页码
            for(var i=firstPage;i<=lastPage;i++){
                obj.pageLabel.push(i);
            }
        },
        //判断当前页是否为第一页
        isTopPage:function(){
            if(this.searchMap.pageNo==1){
                return true;
            }else{
                return false;
            }
        },
        //判断当前页是否为最后一页
        isEndPage:function(){
            if(this.searchMap.pageNo==this.resultMap.totalPages){
                return true;
            }else{
                return false;
            }
        },
        //分页查询,
        queryByPage:function(pageNo){
            if(pageNo<1 || pageNo>this.resultMap.totalPages){
                return ;
            }
            this.searchMap.pageNo=pageNo;
            this.search();//查询
        },

        //添加搜索项  改变searchMap的值
        addSearchItem:function(key,value){
            //如果用户点击的是分类或品牌
            if(key=='category' || key=='brand' || key=='price'){
                this.searchMap[key]=value;

            }else{//用户点击的是规格
                Vue.set(this.searchMap.spec,key,value);
            }
            console.log(this.searchMap);
            this.search();
        },
        //撤销搜索项
        removeSearchItem:function(key){
            //如果用户点击的是分类或品牌
            if(key=='category' || key=='brand' || key=='price' ){
                this.searchMap[key]="";
            }else{//用户点击的是规格
                Vue.delete(this.searchMap.spec,key);
            }
            this.search();
        },
        //判断关键字是否是品牌
        keywordsIsBrand:function(){
            for(var i=0;i< this.resultMap.brandList.length;i++){
                //查看当前的搜索关键字是否在品牌列表当中
                if( this.searchMap.keywords.indexOf( this.resultMap.brandList[i].name )>=0  ){
                    return true;
                }
            }
            return false;
        },


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