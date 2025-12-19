import { Link, useLocation } from 'react-router-dom';
import { LayoutDashboard, CreditCard, TrendingUp, History, Settings, LogOut, User, Banknote } from 'lucide-react';
import { cn } from '@/lib/utils';
import { Button } from '@/components/ui/button';

export default function Sidebar({ className }) {
    const location = useLocation();
    const isActive = (path) => location.pathname === path;

    const menuItems = [
        { icon: LayoutDashboard, label: 'Panel', path: '/dashboard' },
        { icon: CreditCard, label: 'Kartlarım', path: '/cards' },
        { icon: TrendingUp, label: 'Yatırımlar', path: '/invest' },
        { icon: Banknote, label: 'Kredi Başvurusu', path: '/credit-application' },
        { icon: History, label: 'Geçmiş', path: '/history' },
        { icon: Settings, label: 'Ayarlar', path: '/settings' },
    ];

    return (
        <div className={cn("pb-12 min-h-screen border-r bg-card", className)}>
            <div className="space-y-4 py-4">
                <div className="px-3 py-2">
                    <Link to="/">
                        <h2 className="mb-2 px-4 text-2xl font-bold tracking-tight text-primary flex items-center gap-2 cursor-pointer hover:opacity-80 transition-opacity">
                            DinoBank 🏠
                        </h2>
                    </Link>
                    <div className="space-y-1">
                        {menuItems.map((item) => (
                            <Link key={item.path} to={item.path}>
                                <Button
                                    variant={isActive(item.path) ? "secondary" : "ghost"}
                                    className="w-full justify-start"
                                >
                                    <item.icon className="mr-2 h-4 w-4" />
                                    {item.label}
                                </Button>
                            </Link>
                        ))}
                    </div>
                </div>
            </div>
            <div className="px-3 py-2 mt-auto border-t">
                <div className="flex items-center gap-3 px-4 py-4">
                    <div className="h-9 w-9 rounded-full bg-primary/20 flex items-center justify-center text-primary">
                        <User className="h-5 w-5" />
                    </div>
                    <div className="flex-1 overflow-hidden">
                        <p className="text-sm font-medium leading-none truncate">T-Rex Admin</p>
                        <p className="text-xs text-muted-foreground truncate">admin@dinobank.com</p>
                    </div>
                    <Button variant="ghost" size="icon" className="text-destructive hover:text-destructive" onClick={() => {
                        localStorage.removeItem('token');
                        window.location.href = '/login';
                    }}>
                        <LogOut className="h-4 w-4" />
                    </Button>
                </div>
            </div>
        </div>
    );
}
