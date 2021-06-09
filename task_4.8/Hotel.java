public class Hotel {
    Room[] rooms;

    public Hotel(Room[] rooms) {
        this.rooms = rooms;
    }

    public void getFreeRooms(){
        String freeRoomsList = "";
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i].isFree()) freeRoomsList += rooms[i].getRoomNumber()+", ";
        }
        System.out.println("Номера свободных комнат "+freeRoomsList);
    }

    public void reserveRoom(byte roomNumber){
        String info = "Такой комнаты не существует";
        for (int i = 0; i < rooms.length; i++) {
            Room room = rooms[i];
            if(room.getRoomNumber() == roomNumber && room.isFree()){
                room.setFree(false);
                info = ("Комната номер "+roomNumber+" успешно забронированна");
                break;
            }else if(room.getRoomNumber() == roomNumber && !room.isFree()){
                info = "Комната "+roomNumber+" занята";
                break;
            }
        }
        System.out.println(info);
    }

    public void vacateRoom(byte roomNumber) {
        String info = "Такой комнаты не существует";
        for (int i = 0; i < rooms.length; i++) {
            Room room = rooms[i];
            if(room.getRoomNumber() == roomNumber && room.isFree()){
                info = "Комната "+roomNumber+" и так свободна";
                break;
            }else if(room.getRoomNumber() == roomNumber && !room.isFree()){
                room.setFree(true);
                info = ("Комната номер "+roomNumber+" освобождена");
                break;
            }
        }
        System.out.println(info);
    }

    public void getRoomInfo(byte roomNumber) {
        for (int i = 0; i < rooms.length; i++) {
            Room room = rooms[i];
            if(room.getRoomNumber() == roomNumber || roomNumber == -1){
                String info = "";
                info = "Комната "+room.getRoomNumber()+": ";
                info += room.isFree() ? "свободна" : "занята";
                info += ", " + room.getQuantity() + " мест";
                info += room.isWc() ? ", есть санузел" : ", без санузла";
                info += room.isWifi() ? ", с WiFi" : ", без WiFi";
                info += room.isEat() ? ", с едой" : ", без еды";
                System.out.println(info);
                if (roomNumber != -1)
                    return;
            }
        }
        if (roomNumber != -1)
            System.out.println("Такой комнаты не существует");
    }

    public void getRoomsWifi() {
        boolean first = true;
        for (int i = 0; i < rooms.length; i++) {
            Room room = rooms[i];
            if (room.isWifi())
                System.out.print(room.getRoomNumber() + " ");
        }
        System.out.println("");
    }

    public void getRoomsWc() {
        boolean first = true;
        for (int i = 0; i < rooms.length; i++) {
            Room room = rooms[i];
            if (room.isWc())
                System.out.print(room.getRoomNumber() + " ");
        }
        System.out.println("");
    }

    public void getRoomsQuantity(int quantity) {
        boolean first = true;
        for (int i = 0; i < rooms.length; i++) {
            Room room = rooms[i];
            if (room.getQuantity() == quantity)
                System.out.print(room.getRoomNumber() + " ");
        }
        System.out.println("");
    }
}
