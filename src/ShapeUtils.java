import java.awt.Point;
//author Adam Feldscher
//class of universal shape utilities written for use with many programs
public class ShapeUtils {

    public boolean isPtInSquare(Point ptIn, Point sqUpperLeft, int sqWidth, int sqHeight) { //checks if the inputted point is within a square
        if (ptIn.x >= sqUpperLeft.x && ptIn.x <= sqUpperLeft.x + sqWidth) { //if x is in the square range
            if (ptIn.y >= sqUpperLeft.y && ptIn.y <= sqUpperLeft.y + sqHeight) { //if y is in the square range
                return true;
            }
        }
        return false;
    }
    
    public boolean isPtInCircle(Point ptIn, Point cirUpperLeft, int cirWidth, int cirHeight){ //checks if a point is within a circle or elipse
        Point ptCenter = squareUpperLeftToCenterPt(cirUpperLeft, cirWidth, cirHeight);
        double radius = cirWidth / 2;
        if (distanceFormulua(ptIn, ptCenter) <= radius){
            return true;
        }
        return false;
    }
    
    private double distanceFormulua (Point pt1, Point pt2){//finds the distance between two points
        return Math.sqrt(Math.pow(pt2.x-pt1.x, 2) + Math.pow(pt2.y - pt1.y, 2));
    }

    public Point squareUpperLeftToCenterPt(Point upperLeft, int width, int height) { //converts the upper left coordinate of a square to the center point.
        return new Point(upperLeft.x + width / 2, upperLeft.y + height / 2);
    }

    public Point squareCenterToUpperLeftPt(Point center, int width, int height) { //converts the center point of a square to the upper left coordinate.
        return new Point(center.x - width / 2, center.y - height / 2);
    }
    
    public Point rotatePoint(Point in, double angleRAD) { //converts a point to the same point on a rotated axis
    	Point out = new Point();
    	out.x = (int) Math.round(in.x * Math.cos(angleRAD) + in.y * Math.sin(angleRAD));
    	out.y = (int) Math.round(in.y * Math.cos(angleRAD) - in.x * Math.sin(angleRAD));
    	return out;
    }
}
