package utils;

public interface Movable {

	// metodo para realizar o movimento
	void move();

	// metodos para acessar as coordenadas
	double getX();

	double getY();

	// metoos para modificar a coordenada
	void setX(double x);

	void setY(double y);
}