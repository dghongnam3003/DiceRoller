import React, { FC } from 'react';
import { useShortcutsProvider } from './useShortcuts';

interface ShortcutsModalProps {
  // Define any props your modal might use here, e.g., isOpen, onClose
}

const ShortcutsModal: FC<ShortcutsModalProps> = () => {
  const { getShortcuts } = useShortcutsProvider();
  const shortcuts = getShortcuts();

  // Assuming you have a way to close the modal (e.g., onClose prop)
  // and a way to control its visibility (e.g., isOpen prop)
  // The actual implementation depends on your existing modal component
  // and its props. Adapt it to match your component.

  return (
    <div>
      <h2>Keyboard Shortcuts</h2>
      <ul>
        {shortcuts.map((shortcut, index) => (
          <li key={index}>
            <span>{shortcut.combo}</span> - <span>{shortcut.description}</span>
          </li>
        ))}
      </ul>
      {/* Add a close button or mechanism here */} {/* Example only - integrate with the actual close mechanism of your modal component */}
    </div>
  );
};

export default ShortcutsModal;
