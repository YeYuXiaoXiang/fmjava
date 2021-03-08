new Vue({
    el:"#app",
    data:{
        specList:[],

        page:1,
        pageSize:5,
        total:0,
        maxPage:9,
        searchSpec:{
            specName:''
        },
        specEntity:{
            spec:{},
            specOption:[]
        },
        selectedId:[]
    },
    methods:{
        pageHandler:function (page) {
            this.page = page;
            var _this = this;
            axios.post("/spec/search.do?page="+this.page+"&pageSize="+this.pageSize,this.searchSpec).then(function (response) {
                _this.total = response.data.total;
                _this.specList = response.data.rows;
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        addRow:function () {
            this.specEntity.specOption.push({});
        },
        deleteRow:function (index) {
            this.specEntity.specOption.splice(index, 1);
        },
        save:function () {
            var url = '';
            if (this.specEntity.spec.id != null){
                url = "/spec/update.do";
            } else {
                url = "/spec/save.do";
            }

            var _this = this;
            axios.post(url,this.specEntity).then(function (response) {
                if (response.data.success){
                    _this.specEntity.spec = {};
                    _this.specEntity.specList = [];
                    _this.pageHandler(1);
                } else {
                    alert(response.data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
            });
        },
        findSpecWithId:function (id) {
            var _this = this;
            axios.get("/spec/findSpecWithId.do?id="+id).then(function (response) {
                _this.specEntity = response.data;
            }).catch(function (reason) {
                console.log(reason);
            });
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
        deleteSpec:function () {
            var _this = this;
            axios.post("/spec/delete.do",Qs.stringify({idx:this.selectedId},{indices:false})).then(function (response) {
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