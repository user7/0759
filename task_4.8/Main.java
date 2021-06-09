import java.util.Scanner;

/*
*  ***Гостиница***
*  1) Гостиница
*  2) Номера (кол-во мест(1-3), сан.узел(есть/нет), питаение(есть/нет), wifi(есть/нет), свободен/занят)
*
*   *Задание для лабораторной работы №4*
*   Освободить комнату
*   Вывести свойства комнаты
*   Показать комнаты с WiFi
*   Показать комнаты с WC
*   Показать комнаты с Eat
*   Показать комнаты по кол-ву спальных мест
*   Отобразить список команд
* */
public class Main {
    public static void main(String[] args) {
        Room[] rooms = {
                new Room((byte) 1,false,false,true,(byte) 11),
                new Room((byte) 2,true,true,false,(byte) 12),
                new Room((byte) 1,false,true,true,(byte) 13),
                new Room((byte) 3,true,false,false,(byte) 21),
                new Room((byte) 2,false,false,false,(byte) 22),
                new Room((byte) 1,true,true,true,(byte) 23),
                new Room((byte) 3,false,true,false,(byte) 31),
                new Room((byte) 3,true,true,false,(byte) 32),
                new Room((byte) 1,false,false,true,(byte) 33),
        };
        Hotel hotel = new Hotel(rooms);
        Scanner scanner = new Scanner(System.in);
        String command;
        while (true){
            System.out.println("Введите команду (help для помощи):");
            command = scanner.nextLine();
            if(command.equals("getFreeRooms")){
                hotel.getFreeRooms();
            }else if(command.equals("reserveRoom")){
                System.out.print("Введите номер комнаты для бронирования: ");
                byte roomNumber = (byte) scanner.nextInt();
                hotel.reserveRoom(roomNumber);
            }else if (command.equals("vacateRoom")){
                System.out.print("Введите номер комнаты, которую надо освободить: ");
                hotel.vacateRoom((byte) scanner.nextInt());
            }else if (command.equals("getRoomInfo")){
                System.out.print("Введите номер комнаты для отображения: ");
                hotel.getRoomInfo((byte) scanner.nextInt());
            }else if (command.equals("getRoomsWifi")){
                hotel.getRoomsWifi();
            }else if (command.equals("getRoomsWc")){
                hotel.getRoomsWc();
            }else if (command.equals("getRoomsQuantity")){
                System.out.println("Введите количество мест: ");
                hotel.getRoomsQuantity((byte) scanner.nextInt());
            }else if (command.equals("help")){
                System.out.println("Команды:");
                System.out.println(" getFreeRooms");
                System.out.println(" reserveRoom");
                System.out.println(" vacateRoom");
                System.out.println(" getRoomInfo");
                System.out.println(" getRoomsWifi");
                System.out.println(" getRoomsWc");
                System.out.println(" getRoomsQuantity");
                System.out.println(" help");
                System.out.println(" exit");
            }else if (command.equals("exit")){
                break;
            }
        }

    }
}
