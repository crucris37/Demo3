(function(){
    var box;
    function Food(choice) {

        choice = choice || {};
        this.width = choice.width || 20;
        this.height = choice.height || 20;
        this.bgc = choice.bgc || 'orange';
        this.x = choice.x || 0;
        this.y = choice.y || 0;
        this.borderRadius = choice.borderRadius |10;
    }

    Food.prototype.render = function () {
        if(box){
            map.removeChild(box);
        }
        var food = document.createElement('div');
        box = food;
        food.style.width = this.width + 'px';
        food.style.height = this.height + 'px';
        food.style.backgroundColor = this.bgc;
        food.style.position = 'absolute';
        this.x = Tool.getRandom(0, (map.offsetWidth/ this.width-1)) * this.width;
        this.y = Tool.getRandom(0, (map.offsetHeight/ this.height-1)) * this.height;
        food.style.left = this.x + 'px';
        food.style.top = this.y + 'px';
        food.style.borderRadius = this.borderRadius + 'px';

        map.appendChild(food);
    }
    //Food to the whole game
    window.Food = Food;
})();