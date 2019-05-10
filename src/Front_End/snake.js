(function () {
    var array = [];

    function Snake(choice) {
        choice = choice || {};
        this.width = choice.width || 20;
        this.height = choice.height || 20;
        this.body = [
            {x: 3, y: 2, col: 'green'},
            {x: 2, y: 2, col: 'orange'},
            {x: 1, y: 2, col: 'orange'}];
        this.direction = choice.direction || 'right';
    }


    Snake.prototype.render = function () {
        for (var i = 0; i < array.length; i++) {
            map.removeChild(array[i]);
        }
        array.splice(0,array.length);
//what we learned in scala convert to js
        this.body.forEach(function (item) {
            var Node = document.createElement('div');
            array.push(Node);
            Node.style.width = this.width + 'px';
            Node.style.height = this.height + 'px';
            Node.style.position = 'absolute';
            Node.style.left = item.x * this.width + 'px';
            Node.style.top = item.y * this.height + 'px';
            Node.style.backgroundColor = item.col;
            map.appendChild(Node);

        }.bind(this))
    };



    Snake.prototype.move = function () {
        for (var i = this.body.length -1; i >0; i--) {
            this.body[i].x = this.body[i - 1].x;
            this.body[i].y = this.body[i - 1].y;
        }
        //     this.body[0].x +=1;
        switch(this.direction){
            case 'left':
                this.body[0].x -= 1;
                break;
            case 'right':
                this.body[0].x += 1;
                break;
            case 'top':
                this.body[0].y -= 1;
                break;
            case 'bottom':
                this.body[0].y += 1;
                break;
        }
    };

    window.Snake = Snake;
})();