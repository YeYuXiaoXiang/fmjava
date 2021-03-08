new Vue({
    el:"#app",
    data:{
        brandList:[],
        brand:{
            name:'',
            firstChar:''
        },
        page:1,
        pageSize:5,
        total:0,
        maxPage:9,
        selectedId:[],
        searchBrand:{
            name:'',
            firstChar:''
        }
    },
    methods:{
        pageHandler:function (page) {
            this.page = page;
            var _this = this;
            axios.post("/brand/findPage.do?page="+this.page+"&pageSize="+this.pageSize,this.searchBrand).then(function (response) {
                _this.total = response.data.total;
                _this.brandList = response.data.rows;
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        brandSave:function () {
            var url = '';
            if (this.brand.id != null){
                url = "/brand/update.do";
            } else {
                url = "/brand/add.do";
            }

            var _this = this;
            axios.post(url,this.brand).then(function (response) {
             /*  response.data();*/
                if (response.data.success){
                    alert(response.data.message);
                    _this.pageHandler(1);
                }else {
                    alert(response.data.message)
                }
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        findById:function (id) {
            var _this = this;
            axios.get("/brand/findById.do",{params:{id:id}}).then(function (response) {
               _this.brand = response.data;
               console.log(_this.brand);
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        deleteSelection:function (event, id) {
            alert(id);
            if (event.target.checked) {
                this.selectedId.push(id);
            }else{
                var idx = this.selectedId.indexOf(id);
                this.selectedId.splice(idx, 1);
            }
            console.log(this.selectedId);
        },
        deleteBrand:function () {
            var _this = this;
            axios.post("/brand/delete.do",Qs.stringify({idx:this.selectedId},{indices:false})).then(function (response) {
                if (response.data.success){
                    alert(response.data.message);
                    _this.pageHandler(1);
                }else {
                    alert(response.data.message)
                }
            }).catch(function (reason) {
                console.log(reason);
            })
        }
    },
    created:function (){
        this.pageHandler(1);
    }
});