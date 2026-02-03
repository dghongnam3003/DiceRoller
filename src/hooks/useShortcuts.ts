
import { useCallback, useEffect, useRef } from 'react';

interface Shortcut {
  combo: string;
  description: string;
  handler: () => void;
  allowInInputs?: boolean;
}

interface ShortcutRegistry {
  [key: string]: Shortcut;
}

export function useShortcutsProvider() {
  const shortcutsRef = useRef<ShortcutRegistry>({});

  const registerShortcut = useCallback((shortcut: Omit<Shortcut, 'combo'> & { combo: string }) => {
    const combo = shortcut.combo.toLowerCase();
    shortcutsRef.current[combo] = { ...shortcut };
  }, []);

  const unregisterShortcut = useCallback((combo: string) => {
    const normalizedCombo = combo.toLowerCase();
    delete shortcutsRef.current[normalizedCombo];
  }, []);

  const getShortcuts = useCallback(() => {
    return Object.values(shortcutsRef.current);
  }, []);

  const handleKeyDown = useCallback(
    (event: KeyboardEvent) => {
      const target = event.target as HTMLElement;
      if (!target || !('tagName' in target)) {
        return;
      }

      const isInput = target.tagName === 'INPUT' || target.tagName === 'TEXTAREA' || target.isContentEditable;

      const pressedKey = event.key.toLowerCase();
      const ctrlKey = event.ctrlKey || event.metaKey;
      const shiftKey = event.shiftKey;
      const altKey = event.altKey;

      const combo = [
        ctrlKey ? 'ctrl' : '',
        shiftKey ? 'shift' : '',
        altKey ? 'alt' : '',
        pressedKey,
      ]
        .filter(Boolean)
        .join('+')
        .toLowerCase();

      const shortcut = shortcutsRef.current[combo];
      if (shortcut && (!isInput || shortcut.allowInInputs !== false)) {
        event.preventDefault();
        shortcut.handler();
      }
    },
    []
  );

  useEffect(() => {
    document.addEventListener('keydown', handleKeyDown);
    return () => {
      document.removeEventListener('keydown', handleKeyDown);
    };
  }, [handleKeyDown]);

  const openShortcuts = useCallback(() => {
    // Implement modal opening logic here, e.g., setting a state variable
    // that controls the modal visibility.
    // Since the actual implementation depends on your existing modal component,
    // leave it as a comment for now.
  }, []);

  return {
    registerShortcut,
    unregisterShortcut,
    getShortcuts,
    openShortcuts,
  };
}
