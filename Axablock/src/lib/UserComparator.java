package src.lib;
class UserComparator implements Comparator<User> {
    @Override
    public int compare(User u1, User u2) {
        return Double.compare(u1.getCurrentBalance(), u2.getCurrentBalance());
    }
}


