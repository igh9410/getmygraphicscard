class ItemModel {
  title: string;
  link: string;
  image: string;
  lprice: number;

  constructor(title: string, link: string, image: string, lprice: number) {
    this.title = title;
    this.link = link;
    this.image = image;
    this.lprice = lprice;
  }
}

export default ItemModel;
